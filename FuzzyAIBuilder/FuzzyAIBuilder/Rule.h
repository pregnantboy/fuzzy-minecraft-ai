//
//  Rule.h
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 26/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "RuleInput.h"
#import "RuleOutput.h"
@interface Rule : NSObject

- (instancetype) init;
- (RuleInput *)getRuleAtIndex:(NSInteger) index;
- (void)createNewRuleInput;
- (void)deleteRuleInput:(NSInteger)index;
- (void)setOperator:(NSString *)operator atIndex:(NSInteger)index;
- (void)setEquality:(NSString *)equality atIndex:(NSInteger)index;
- (void)setCond:(NSString *)cond atIndex:(NSInteger) index;
- (void)setValue:(NSString *)value atIndex:(NSInteger) index;
- (void)setAction:(NSString *)act;
- (void)setModifier:(NSString *)mod;

- (NSString *)getCondAtIndex:(NSInteger)index ;
- (NSString *)getValueAtIndex:(NSInteger)index;
- (NSString *)getOperatorAtIndex:(NSInteger)index;
- (NSString *)getEqualityAtIndex:(NSInteger)index;
- (NSString *)getAction;
- (NSString *)getModifier;

- (NSArray *)getPossibleValuesAtIndex:(NSInteger) index;
- (NSArray *)getPossibleModifiers;
- (NSInteger)getNumInputs;
@end
