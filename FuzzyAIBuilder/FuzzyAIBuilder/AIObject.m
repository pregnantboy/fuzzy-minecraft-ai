//
//  AIObject.m
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 29/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import "AIObject.h"
#define ruleListKey @"ruleListKey"
#define nameKey @"nameKey"

@interface AIObject () {
    NSMutableArray *ruleList;
    NSString *name;
}

@end

@implementation AIObject

- (instancetype)initWithAIName:(NSString *)AIName {
    self = [super init];
    if (self) {
        name = AIName;
        ruleList = [[NSMutableArray alloc] init];
    }
    return self;
}

- (instancetype)initWithAIName:(NSString *)AIName andRuleList:(NSMutableArray *)aRuleList {
    self = [super init];
    if (self) {
        name = AIName;
        ruleList = aRuleList;
    }
    return self;
}

- (void)saveNewRule:(Rule*)newRule {
    [ruleList addObject:newRule];
}

- (Rule *)getRuleAtIndex:(NSInteger) index {
    return [ruleList objectAtIndex:index];
}

- (void)replaceExistingRule:(NSInteger) index withRule: (Rule*)modifiedRule {
    [ruleList replaceObjectAtIndex:index withObject:modifiedRule];
}

- (void)deleteRuleAtIndex:(NSInteger) index {
    [ruleList removeObjectAtIndex:index];
}

- (BOOL)moveUpRule:(NSInteger)index {
    if (index < [ruleList count] && index >0) {
        Rule *temp = [ruleList objectAtIndex:index];
        [ruleList removeObjectAtIndex:index];
        [ruleList insertObject:temp atIndex:index-1];
        return YES;
    }
    return NO;
}

- (BOOL)moveDownRule:(NSInteger)index {
    if (index>=0 && index< ([ruleList count]-1)) {
        Rule *temp = [ruleList objectAtIndex:index];
        [ruleList removeObjectAtIndex:index];
        [ruleList insertObject:temp atIndex:index+1];
        return YES;
    }
    return NO;
}

- (NSMutableArray *)ruleList {
    return ruleList;
}

- (NSInteger)getNumRules {
    return [ruleList count];
}

- (NSString *)getName {
    return name;
}

#pragma mark - NSCoding
- (instancetype)initWithCoder:(NSCoder *)aDecoder {
    return [self initWithAIName:[aDecoder decodeObjectForKey:nameKey] andRuleList:[[aDecoder decodeObjectForKey:ruleListKey] mutableCopy]];
}

- (void)encodeWithCoder:(NSCoder *)aCoder {
    [aCoder encodeObject:name forKey:nameKey];
    [aCoder encodeObject:ruleList forKey:ruleListKey];
}

@end
