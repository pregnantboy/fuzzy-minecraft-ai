//
//  RuleCondition.m
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 26/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import "RuleCondition.h"

@interface RuleCondition() {
    NSString* condCode;
    NSString* valueCode;
}
@end


@implementation RuleCondition

+ (NSDictionary *)conditions {
    static NSDictionary *conditions = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        conditions = @{
                       @"DistFromNearestEnemy": @[@"Near", @"Far", @"Out-of-Range"],
                       @"Health": @[@"Low", @"Medium", @"High"],
                       @"Mana": @[@"Low", @"Medium", @"High"],
                       @"ArrowCount" : @[@"Low", @"Medium", @"High"],
                       @"NumEnemies": @[@"None", @"Few" ,@"Many" ,@"OutNumbered"],
                       @"NumAllies" : @[@"None", @"Few" , @"Many" ,@"OutNumbered"],
                       @"PlayerHealth":@[@"Low", @"Medium", @"High"],
                       @"Sky": @[@"Bright" , @"Dark"],
                       @"PlayerHunger": @[@"Starving", @"Satisfied", @"Bloated"],
                       @"OreCount": @[@"Depleting", @"Healthy"],
                       @"BuildingBlockCount": @[@"Depleting", @"Healthy"],
                       @"Weather": @[@"Clear", @"Rain", @"Thunderstorms"],
                       @"NearestEnemyStrength": @[@"Noob", @"AverageJoe", @"EpicBoss"],
                       @"PlayerTargetStrength": @[@"Noob", @"AverageJoe", @"EpicBoss"],
                       @"PlayerAttackerStrength": @[@"Noob", @"AverageJoe", @"EpicBoss"],
                       @"AttackerStrength" :@[@"Noob", @"AverageJoe", @"EpicBoss"]  // applies only when currently attacked
                       };
    });
    return conditions;
}

- (instancetype) init {
    self = [super init];
    if (self) {
        condCode = nil;
    }
    return self;
}

- (instancetype) initWithCond:(NSString *)cond {
    self = [super init];
    if (self) {
        condCode = cond;
    }
    return self;
}

- (instancetype) initWithCond:(NSString *)cond value:(NSString *)valCode {
    self = [super init];
    if (self) {
        condCode = cond;
        valueCode = valCode;
    }
    return self;
}

- (void)setCondCode:(NSString *)cond {
    if ([[RuleCondition getPossibleConditions] containsObject:cond]) {
        condCode = cond;
        valueCode = nil;
    }
}

- (void)setCondValue: (NSString *)value {
    if ([[self getPossibleValues] containsObject:value]) {
        valueCode = value;
    }
}

- (NSString *)value {
    return valueCode;
}

- (BOOL)isSet {
    if (condCode != nil && valueCode != nil ) {
        if ([[RuleCondition getPossibleConditions] containsObject:condCode] && [[RuleCondition getPossibleValueForCond:condCode] containsObject:valueCode]) {
            return YES;
        }
    }
    return NO;
}

- (NSString *)cond {
    return condCode;
}

- (NSArray *)getPossibleValues {
    if (condCode != nil) {
        return (NSArray *)[[RuleCondition conditions] valueForKey:condCode];
    } else {
        return nil;
    }
}

+ (NSArray *)getPossibleConditions {
    return [[[RuleCondition conditions] allKeys] sortedArrayUsingSelector:@selector(localizedCaseInsensitiveCompare:)];
}

+ (NSArray *)getPossibleValueForCond:(NSString *)cond {
    if (cond != nil) {
        return (NSArray *)[[RuleCondition conditions] valueForKey:cond];
    } else {
        return nil;
    }
}

@end
