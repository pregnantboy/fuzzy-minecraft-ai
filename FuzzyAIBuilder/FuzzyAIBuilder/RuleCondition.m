//
//  RuleCondition.m
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 26/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import "RuleCondition.h"

@implementation RuleCondition



+ (NSDictionary *)conditions {
    static NSDictionary *conditions;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        conditions = @{
                       @"DistFromNearestEnemy": @[@"Near", @"Far", @"Out-of-Range"],
                       @"Health": @[@"Low", @"Medium", @"High"],
                       @"Mana": @[@"Low", @"Medium", @"High"],
                       @"ArrowCount" : @[@"Low", @"Medium", @"High"],
                       @"NumEnemies": @[@"None", @"Few" , @"OutNumbered"],
                       @"NumAllies" : @[@"None", @"Few" , @"OutNumbered"],
                       @"PlayerHealth":@[@"Low", @"Medium", @"High"],
                       @"Brightness": @[@"Bright" , @"Dark"],
                       @"PlayerHunger": @[@"Starving", @"Hungry", @"Bloated"],
                       @"OreCount": @[@"Depleting", @"Healthy"],
                       @"BuildingBlockCount": @[@"Depleting", @"Healthy"],
                       @"Rain": @[@"Clear", @"Rain", @"Thunderstorms"],
                       @"NearestEnemyStrength": @[@"Noob", @"AverageJoe", @"Boss-level"],
                       @"PlayerTargetStrength": @[@"Noob", @"AverageJoe", @"Boss-level"],
                       @"PlayerAttackerStrength": @[@"Noob", @"AverageJoe", @"Boss-level"],
                       @"AttackerStrength" :@[@"Noob", @"AverageJoe", @"Boss-level"]  // applies only when currently attacked
                       };
    });
    return conditions;
}

- (instancetype) init {
    self = [super init];
    if (self) {
        self.condCode = nil;
    }
    return self;
}

- (instancetype) initWithCode:(NSString *)code {
    self = [super init];
    if (self) {
        self.condCode = code;
    }
    return self;
}

- (instancetype) initWithCode:(NSString *)code value:(NSString *)valueCode {
    self = [super init];
    if (self) {
        self.condCode = code;
        self.valueCode = valueCode;
    }
    return self;
}

- (void)setCond:(NSString *)condCode {
    if ([[RuleCondition getPossibleConditions] containsObject:condCode]) {
        self.condCode = condCode;
        self.valueCode = nil;
    }
}

- (void)setCondValue: (NSString *)value {
    if ([[self getPossibleValues] containsObject:value]) {
        self.valueCode = value;
    }
}

- (BOOL)isSet {
    if (self.condCode != nil && self.valueCode != nil ) {
        if ([[RuleCondition getPossibleConditions] containsObject:self.condCode] && [[RuleCondition getPossibleValueForCond:self.condCode] containsObject:self.valueCode]) {
            return YES;
        }
    }
    return NO;
}

- (NSArray *)getPossibleValues {
    if (self.condCode != nil) {
        return (NSArray *)[[self conditions] valueForKey:self.condCode];
    } else {
        return nil;
    }
}

+ (NSArray *)getPossibleConditions {
    return [[RuleCondition conditions] allKeys];
}

+ (NSArray *)getPossibleValueForCond:(NSString *)cond {
    if (cond != nil) {
        return (NSArray *)[[RuleCondition conditions] valueForKey:cond];
    } else {
        return nil;
    }
}

@end
